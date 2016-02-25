package com.pam.codenamehippie.ui.view.trianglemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.pam.codenamehippie.R;

/**
 * Created by Catherine on 2015-12-09.
 */
public class TriangleImageView extends ImageView {

    //Angle est utilisé pour le positionnement sur le cercle
    private float angle = 0;
    //Position qui représente l'indice dans le tableau enfants viewgroup
    private int position = 0;
    // Le nom de la view
    private String name;

    /**
     * Retourner l'angle de la vue.
     * @return Renvoie l'angle de vue en degrés.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Réglez l'angle de la vue.
     * @param angle L'angle doit être réglé pour la vue.
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Retourne la position de la vue.
     * @return Retourne la position de la vue.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the position of the view.
     * @param position The position to be set for the view.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Réglez la position de la vue
     * @return Retourne le nom de la vue.
     */
    public String getName(){
        return name;
    }


    /**
     * Définissez le nom de la vue.
     * @param name Le nom à définir pour la vue.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param context
     */
    public TriangleImageView(Context context) {
        this(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public TriangleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public TriangleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs,
                    R.styleable.TriangleImageView);

            this.name = array.getString(R.styleable.TriangleImageView_name);
        }
    }

}
