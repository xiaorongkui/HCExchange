package com.hacai.exchange.module.home.view;

import android.widget.TextView;

import com.hacai.exchange.R;
import com.hacai.exchange.base.BaseActivity;
import com.hacai.exchange.bean.PdfFileInfo;
import com.hacai.exchange.common.Constant;
import com.hacai.exchange.utils.LogUtil;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;

import static java.lang.String.format;

/**
 * Created by lenovo on 2017/12/27.
 */

public class ShowPDFActivity extends BaseActivity implements OnPageChangeListener {

    private PDFView pdfView;
    private TextView title;
    private String pdfUrlPath;
    private String pdfName;
    private Integer pageNumber = 1;

    @Override
    protected void initView() {
        pdfView = (PDFView) findViewById(R.id.pdfView);
        title = (TextView) findViewById(R.id.title_header_tv);
        PdfFileInfo pwdfInfo = getIntent().getParcelableExtra(Constant.AGREEMENT_PDF_INFO);
        pdfUrlPath = pwdfInfo.getFilePath();
        pdfName = pwdfInfo.getFileName();
        title.setText(pdfName);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf_view;
    }

    @Override
    protected String getSimpleNme() {
        return getClass().getSimpleName();
    }

    private void display(File file, boolean jumpToFirstPage) {
        if (jumpToFirstPage) pageNumber = 1;
        pdfView.fromFile(file).defaultPage(pageNumber).onPageChange(this).onLoad(new OnLoadCompleteListener() {
            @Override
            public void loadComplete(int nbPages) {
                LogUtil.i("pdf加载完成");
            }
        }).load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        title.setText(format("%s %s / %s", pdfName, page, pageCount));
    }
}
