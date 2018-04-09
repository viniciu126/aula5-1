package br.usjt.ads.desmob.paises;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.Hashtable;

import static br.usjt.ads.desmob.paises.R.drawable.bandeira;

/**
 * Created by Vinicius Maciel on 18/03/2018.
 * RA 816117960
 */

public class PaisAdapter extends BaseAdapter implements SectionIndexer {
    private Pais[] paises;
    private Activity activity;
    Object[] sectionHeaders;
    Hashtable<Integer, Integer> positionForSectionMap;
    Hashtable<Integer, Integer> sectionForPositionMap;

    public PaisAdapter(Pais[] paises, Activity activity) {
        this.paises = paises;
        this.activity = activity;
        sectionHeaders = SectionIndexBuilder.buildSectionHeaders(paises);
        positionForSectionMap = SectionIndexBuilder.buildPositionForSectionMap(paises);
        sectionForPositionMap = SectionIndexBuilder.buildSectionForPositionMap(paises);
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    public int getCount() {
        return paises.length;
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    public Object getItem(int position) {
        return paises[position];
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_pais, parent, false);
            ImageView bandeira = (ImageView) view.findViewById(R.id.foto_pais);
            TextView nome = (TextView) view.findViewById(R.id.texto_nome_pais);
            TextView detalhe = (TextView) view.findViewById(R.id.texto_detalhe_pais);
            ViewHolder viewHolder = new ViewHolder(bandeira, nome, detalhe);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.getNome().setText(paises[position].getNome());
        viewHolder.getDetalhe().setText(String.format("região: %s - capital: %s",
                paises[position].getRegiao(),
                paises[position].getCapital()));
        Drawable drawable = Util.getDrawable(activity, paises[position].getCodigo3().toLowerCase());
        if(drawable == null){
            drawable = activity.getDrawable(bandeira);
        }
        viewHolder.getBandeira().setImageDrawable(drawable);

        return view;
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    public Object[] getSections() {
        return sectionHeaders;
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionForSectionMap.get(sectionIndex).intValue();
    }
    /**
     * Created by Vinicius Maciel on 18/03/2018.
     * RA 816117960
     */
    @Override
    public int getSectionForPosition(int position) {
        return sectionForPositionMap.get(position).intValue();
    }
}
