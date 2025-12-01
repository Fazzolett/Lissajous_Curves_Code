package com.Lissajous_Curves2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class UIStage extends Stage {
    // x = Asin(at+d)
    // y = Bsin(bt)

    float minA = 5f;
    float maxA = 10f;
    float stepSizeA = 0.1f;
    float minB = 5f;
    float maxB = 10f;
    float stepSizeB = 0.1f;
    float mina = 1f;
    float maxa = 10f;
    float stepSizea = 0.1f;
    float minb = 1f;
    float maxb = 10f;
    float stepSizeb = 0.1f;
    float mind = 0;
    float maxd = (float) (2*Math.PI);
    float stepSized = maxd/100f;
    //float minResolution = 1f;
    //float maxResolution = 100f;
    //float stepSizeResolution = 1f;

    float sliderPosX = 40;
    float sliderPosY = 1000;
    float sliderWidth = 300 - sliderPosX;
    float sliderHeight = 90;

    private Slider.SliderStyle sliderStyle;
    private Label.LabelStyle labelStyle;
    private SliderBundle sliderBundle_A;
    private SliderBundle sliderBundle_B;
    private SliderBundle sliderBundle_a;
    private SliderBundle sliderBundle_b;
    private SliderBundle sliderBundle_d;
    //private SliderBundle sliderBundle_Resolution;

    private static class SliderBundle{
        Slider slider;
        Label label;

        public SliderBundle(Slider slider, Label label){
            this.slider = slider;
            this.label = label;
        }
    }

    public UIStage(float cameraWidth, float cameraHeight){
        super(new FitViewport(cameraWidth, cameraHeight));
        createSliderStyle();
        createLabelStyle();

        Label formulaLabel = new Label("x = Asin(at+d), y = Bsin(bt)", labelStyle);
        formulaLabel.setBounds(sliderPosX, sliderPosY, 200, sliderHeight);
        this.addActor(formulaLabel);

        sliderBundle_A = createSliderBundle(minA, maxA, stepSizeA);
        sliderBundle_B = createSliderBundle(minB, maxB, stepSizeB);
        sliderBundle_a = createSliderBundle(mina, maxa, stepSizea);
        sliderBundle_b = createSliderBundle(minb, maxb, stepSizeb);
        sliderBundle_d = createSliderBundle(mind, maxd, stepSized);

        //sliderBundle_Resolution = createResolutionSlider(minResolution, maxResolution, stepSizeResolution);
    }


    @Override
    public void act(float deltaTime){
        sliderBundle_A.label.setText("A = " + String.format("%.1f", sliderBundle_A.slider.getValue()));
        sliderBundle_B.label.setText("B = " + String.format("%.1f", sliderBundle_B.slider.getValue()));
        sliderBundle_a.label.setText("a = " + String.format("%.1f", sliderBundle_a.slider.getValue()));
        sliderBundle_b.label.setText("b = " + String.format("%.1f", sliderBundle_b.slider.getValue()));
        sliderBundle_d.label.setText("d = " + String.format("%.2f", sliderBundle_d.slider.getValue()));
        //sliderBundle_Resolution.label.setText("Resolution = " + String.format("%.0f", sliderBundle_Resolution.slider.getValue()) + "%");
    }
/*
    private SliderBundle createResolutionSlider(float minResolution, float maxResolution, float stepSizeResolution) {
        Label label = new Label("", labelStyle);
        label.setBounds(sliderPosX, sliderPosY - sliderHeight, 80, sliderHeight);
        this.addActor(label);
        sliderPosY -= sliderHeight;
        Slider slider = new Slider(minResolution, maxResolution, stepSizeResolution, false, sliderStyle);
        slider.setBounds(sliderPosX, sliderPosY-sliderHeight, sliderWidth, sliderHeight);
        slider.setTouchable(Touchable.enabled);
        this.addActor(slider);
        sliderPosY -= sliderHeight;
        return new SliderBundle(slider, label);
    }
*/
    private SliderBundle createSliderBundle(float min, float max, float stepSize){
        Slider slider = new Slider(min, max, stepSize, false, sliderStyle);
        slider.setBounds(sliderPosX, sliderPosY-sliderHeight, sliderWidth, sliderHeight);
        slider.setTouchable(Touchable.enabled);
        Label label = new Label("", labelStyle);
        label.setBounds(sliderPosX + sliderWidth + 20, sliderPosY - sliderHeight, 80, sliderHeight);
        this.addActor(slider);
        this.addActor(label);
        sliderPosY -= sliderHeight;
        return new SliderBundle(slider, label);
    }

    //ChatGPT code for style
    private void createSliderStyle(){
        Texture barTex   = createRoundedRect(300, 8, Color.DARK_GRAY);
        Texture fillTex  = createRoundedRect(300, 8, new Color(0.2f, 0.6f, 1f, 1f));
        Texture knobTex  = createCircle(48, Color.WHITE);
        Texture knobDownTex = createCircle(48, new Color(0.9f, 0.9f, 0.9f, 1f));

        Drawable bar  = new TextureRegionDrawable(new TextureRegion(barTex));
        Drawable knob = new TextureRegionDrawable(new TextureRegion(knobTex));
        Drawable knobDown = new TextureRegionDrawable(new TextureRegion(knobDownTex));
        Drawable fill = new TextureRegionDrawable(new TextureRegion(fillTex));

        // ===== Slider Style =====
        sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = bar;
        sliderStyle.knob = knob;
        sliderStyle.knobDown = knobDown;
        sliderStyle.knobOver = knob;
        sliderStyle.knobBefore = fill;  // makes the bar fill left side of knob
    }

    public void createLabelStyle(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/Fonts/ArialBold.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 64;
        BitmapFont font = generator.generateFont(parameter);
        font.getData().setScale(0.6f);
        generator.dispose();

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = new Color(1, 1, 1, 1f);
    }

    private Texture createRoundedRect(int width, int height, Color color) {
        Pixmap pm = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pm.setBlending(Pixmap.Blending.SourceOver);
        pm.setColor(color);
        int radius = height / 2;
        pm.fillCircle(radius, radius, radius);
        pm.fillCircle(width - radius - 1, radius, radius);
        pm.fillRectangle(radius, 0, width - height, height);

        Texture tex = new Texture(pm);
        pm.dispose();
        return tex;
    }

    private Texture createCircle(int diameter, Color color) {
        Pixmap pm = new Pixmap(diameter, diameter, Pixmap.Format.RGBA8888);
        pm.setColor(color);
        pm.fillCircle(diameter / 2, diameter / 2, diameter / 2);

        Texture tex = new Texture(pm);
        pm.dispose();
        return tex;
    }

    public float getA(){
        return sliderBundle_A.slider.getValue();
    }
    public float getB(){
        return sliderBundle_B.slider.getValue();
    }
    public float geta(){
        return sliderBundle_a.slider.getValue();
    }
    public float getb(){
        return sliderBundle_b.slider.getValue();
    }
    public float getd(){
        return sliderBundle_d.slider.getValue();
    }
    public float getResolution(){
        //return sliderBundle_Resolution.slider.getValue();
        return 50f;
    }
}
