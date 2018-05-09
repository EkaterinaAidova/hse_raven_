package com.hse.raven;

import android.content.Context;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.vuforia.VuforiaManager;
import org.rajawali3d.vuforia.VuforiaRenderer;

import javax.microedition.khronos.opengles.GL10;

public class RavenVuforiaRenderer extends VuforiaRenderer{
    private DirectionalLight mLight;
    // private SkeletalAnimationObject3D mBob;
    // private Object3D mF22;
    // private Object3D mAndroid;
    private Raven ravenModel;

    //private Vector3 woodPosition;
    //private Quaternion woodOrientation;

    // private Animation3D caterpieFeedAnim;

    public RavenVuforiaRenderer(Context context, VuforiaManager vm) {
        super(context, vm);
    }

    private Object3D loadObj(int resourceId, double scale) {
        Object3D object = null;
        try {
            LoaderOBJ objParser = new LoaderOBJ(mContext.getResources(), mTextureManager, resourceId);
            objParser.parse();
            object = objParser.getParsedObject();
            object.setScale(scale);
            getCurrentScene().addChild(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    protected void initScene() {
        mLight = new DirectionalLight(.1f, 0, -1.0f);
        mLight.setColor(1.0f, 1.0f, 0.8f);
        mLight.setPower(1);
        getCurrentScene().addLight(mLight);
        ravenModel = new Raven(getCurrentScene(), loadObj(R.raw.raven_obj, 50), null);
    }
    @Override
    protected void foundImageMarker(String trackableName, Vector3 position, Quaternion orientation) {
        ravenModel.show(position, orientation);
    /*    if (trackableName.equals("wood")) {
            woodPosition = position;
            woodOrientation = orientation;
        }

        if (trackableName.equals("rectangle")) {
            pCaterpie.show(position, orientation, true);
        }

        if (trackableName.equals("circle")) {
            pPikachu.show(position, orientation, true);
        }

        // -- also handle cylinder targets here
        // if (trackableName.equals("CylinderApp")) {
        // }*/

        // -- also handle multi-targets here
    }

   /* @Override
    protected void onButtonPressed(String trackableName, String buttonName) {
        if (trackableName.equals("circle")) {
            if (buttonName.equals("feed")) {
                pPikachu.feed();
            } else if (buttonName.equals("reset")) {
                pPikachu.reset();
            }
        } else if (trackableName.equals("rectangle")) {
            if (buttonName.equals("feed")) {
                pCaterpie.feed();
            } else if (buttonName.equals("reset")) {
                pCaterpie.reset();
            }
        } else if (trackableName.equals("wood")) {
            if (buttonName.equals("red")) {
                pCharmander.show(woodPosition, woodOrientation);
            } else if (buttonName.equals("blue")) {
                pSquirtle.show(woodPosition, woodOrientation);
            } else if (buttonName.equals("green")) {
                pBulbasaur.show(woodPosition, woodOrientation);
            } else if (buttonName.equals("yellow")) {
                pPikachu.show(woodPosition, woodOrientation);
            }
        }
    }*/

    @Override
    protected void foundFrameMarker(int markerId, Vector3 position, Quaternion orientation) {
        ravenModel.show(position, orientation);

        /*switch (markerId) {
            case 0:
                pPikachu.show(position, orientation);
                break;
            case 1:
                pRaichu.show(position, orientation);
                break;
            case 2:
                pRiolu.show(position, orientation);
                break;
            case 3:
                pLucario.show(position, orientation);
                break;
            case 4:
                pCaterpie.show(position, orientation);
                break;
            case 5:
                pTransel.show(position, orientation);
                break;
            case 6:
                pButterfree.show(position, orientation);
                break;
            case 7:
                pBulbasaur.show(position, orientation);
                break;
            case 8:
                pCharmander.show(position, orientation);
                break;
            case 9:
                pSquirtle.show(position, orientation);
                break;
            default:
                break;
        }*/
    }

    @Override
    public void noFrameMarkersFound() {
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }
}
