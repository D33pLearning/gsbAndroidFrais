package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.DatePicker;


import java.util.ArrayList;
import java.util.Locale;

class FraisHfAdapter extends BaseAdapter {

	private final ArrayList<FraisHf> lesFrais ; // liste des frais du mois
	private final LayoutInflater inflater ;
	private final Context context;

	/**
	 * Constructeur de l'adapter pour valoriser les propriétés
	 * @param context Accès au contexte de l'application
	 * @param lesFrais Liste des frais hors forfait
	 */
	public FraisHfAdapter(Context context, ArrayList<FraisHf> lesFrais) {
		inflater = LayoutInflater.from(context) ;
		this.lesFrais = lesFrais;
		this.context = context;

	}

	/**
	 * retourne le nombre d'éléments de la listview
	 */
	@Override
	public int getCount() {
		return lesFrais.size() ;
	}

	/**
	 * retourne l'item de la listview à un index précis
	 */
	@Override
	public Object getItem(int index) {
		return lesFrais.get(index) ;
	}

	/**
	 * retourne l'index de l'élément actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour ;
		TextView txtListMontant ;
		TextView txtListMotif ;
		ImageButton cmdSuppHf;
	}

	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(final int index, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.layout_liste, parent, false) ;
			holder.txtListJour = convertView.findViewById(R.id.txtListJour);
			holder.txtListMontant = convertView.findViewById(R.id.txtListMontant);
			holder.txtListMotif = convertView.findViewById(R.id.txtListMotif);
			holder.cmdSuppHf= convertView.findViewById(R.id.cmdSuppHf);
			convertView.setTag(holder) ;
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.txtListJour.setText(String.format(Locale.FRANCE, "%d", lesFrais.get(index).getJour()));
		holder.txtListMontant.setText(String.format(Locale.FRANCE, "%.2f", lesFrais.get(index).getMontant())) ;
		holder.txtListMotif.setText(lesFrais.get(index).getMotif()) ;

		// si on clique sur la croix alors on supprime l'élement à l'index correspondant
		holder.cmdSuppHf.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Integer annee = ((DatePicker)((HfRecapActivity) context).findViewById(R.id.datHfRecap)).getYear() ;
				Integer mois = ((DatePicker)((HfRecapActivity) context).findViewById(R.id.datHfRecap)).getMonth() + 1 ;
				Integer jour = ((DatePicker)((HfRecapActivity) context).findViewById(R.id.datHfRecap)).getDayOfMonth() ;
				ArrayList<FraisHf> lesFraisHf;

				Integer key = annee * 100 + mois;
				lesFraisHf = Global.listFraisMois.get(key).getLesFraisHf();
				lesFraisHf.remove(index);
				Serializer.serialize(Global.listFraisMois,context);
				notifyDataSetInvalidated();
			}
		});
		return convertView ;
	}

}
