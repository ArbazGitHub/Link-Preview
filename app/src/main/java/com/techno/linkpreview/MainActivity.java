package com.techno.linkpreview;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leocardz.link.preview.library.LinkPreviewCallback;
import com.leocardz.link.preview.library.SourceContent;
import com.leocardz.link.preview.library.TextCrawler;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    EditText etLink;
    Button btnSubmit;
    LinearLayout llTitleView;
    LinearLayout llWholeView;

    TextView tvTitle, tvWTitle, tvWLink;
    ImageView ivLinkImg, ivCross;

    private TextCrawler textCrawler;

    private Bitmap[] currentImageSet;
    private Bitmap currentImage;
    private int currentItem = 0;
    private int countBigImages = 0;
    private boolean noThumb;

    private String currentTitle, currentUrl, currentCannonicalUrl,
            currentDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textCrawler = new TextCrawler();
        initViews();
        listener();
    }

    public void initViews() {
        try {
            etLink = findViewById(R.id.etLink);

            llTitleView = findViewById(R.id.llTitleView);
            llWholeView = findViewById(R.id.llWholeView);

            tvTitle = findViewById(R.id.tvTitle);
            tvWTitle = findViewById(R.id.tvWTitle);
            tvWLink = findViewById(R.id.tvWLink);

            ivLinkImg = findViewById(R.id.ivLinkImg);
            ivCross = findViewById(R.id.ivCross);

            btnSubmit = findViewById(R.id.btnSubmit);

            etLink.setText("https://youtu.be/OB9tnVOrUmI");
            etLink.setSelection(etLink.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listener() {
        try {
            etLink.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                }
            });

            ivCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (llWholeView.getVisibility() == View.VISIBLE) {
                        llWholeView.setVisibility(View.GONE);
                    }
                }
            });
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String linkStr = "";
                        linkStr = etLink.getText().toString();
                        if (!linkStr.equals("")) {
                            textCrawler.makePreview(callback, linkStr);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    LinkPreviewCallback callback = new LinkPreviewCallback() {

        @Override
        public void onPre() {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPos(SourceContent sourceContent, boolean isNull) {
            try {
                Log.e("Data:-", "" + sourceContent);
                if (isNull || sourceContent.getFinalUrl().equals("")) {

                } else {
                    llWholeView.setVisibility(View.VISIBLE);
                    tvWTitle.setText(sourceContent.getTitle());
                    tvWLink.setText(sourceContent.getUrl());
                    if (sourceContent.getImages().get(0) != null && !sourceContent.getImages().get(0).equals("")) {
                        Picasso.with(MainActivity.this).load(sourceContent.getImages().get(0)).into(ivLinkImg);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
}
