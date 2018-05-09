package com.hse.raven;

import android.media.MediaPlayer;

import org.rajawali3d.Object3D;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.scene.Scene;

import java.util.TimerTask;

public class Raven {
    private Scene mScene;
    private Object3D mModel;
    private Raven mEvolved;;
    private Vector3 origScale;
    private MediaPlayer sound;

    public Raven(Scene scene, Object3D model, Raven evolved) {
        this(scene, model, evolved, null);
    }

    public Raven(Scene scene, Object3D model, Raven evolved, MediaPlayer mp) {
        this.mScene = scene;
        this.mModel = model;
        this.origScale = model.getScale();
        this.sound = mp;

        if (evolved != null) {
            this.mEvolved = evolved;
        }
    }

    public void show(Vector3 position, Quaternion orientation) {
        this.show(position, orientation, false);
    }

    public void show(Vector3 position, Quaternion orientation, boolean interactive) {
            this.mEvolved.show(position, orientation, interactive);
    }

    public void reset() {
        if (this.mEvolved != null) {
            this.mEvolved.reset();
        }
    }

}