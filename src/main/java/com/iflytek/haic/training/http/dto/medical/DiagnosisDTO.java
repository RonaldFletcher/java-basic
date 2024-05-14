package com.iflytek.haic.training.http.dto.medical;

/**
 * 病例数据中的诊断数据
 */
public class DiagnosisDTO {
    /**
     * 诊断编码")
     */
    private String diagnosticCode;

    /**
     * 诊断名称")
     */
    private String diagnosticName;

    /**
     * 诊断类型（Z：中医；空：西医）")
     */
    private String diagTypeCode;

    /**
     * 诊断标志（0：次要诊断；1：主要诊断）")
     */
    private String diagnosticMainSign;

    /**
     * 诊断序号")
     */
    private Integer diagSort;

    /**
     * 诊断疑似标志（0：否；1：是）")
     */
    private String isSuspected;

    /**
     * 诊断发病时间（yyyy-MM-dd HH:mm:ss）")
     */
    private String morbidityDate;

    /**
     * 诊断备注")
     */
    private String diagnosticNote;

    /**
     * 症型")
     */
    private String subSyndrome;

    public String getDiagnosticCode() {
        return diagnosticCode;
    }

    public void setDiagnosticCode(String diagnosticCode) {
        this.diagnosticCode = diagnosticCode;
    }

    public String getDiagnosticName() {
        return diagnosticName;
    }

    public void setDiagnosticName(String diagnosticName) {
        this.diagnosticName = diagnosticName;
    }

    public String getDiagTypeCode() {
        return diagTypeCode;
    }

    public void setDiagTypeCode(String diagTypeCode) {
        this.diagTypeCode = diagTypeCode;
    }

    public String getDiagnosticMainSign() {
        return diagnosticMainSign;
    }

    public void setDiagnosticMainSign(String diagnosticMainSign) {
        this.diagnosticMainSign = diagnosticMainSign;
    }

    public Integer getDiagSort() {
        return diagSort;
    }

    public void setDiagSort(Integer diagSort) {
        this.diagSort = diagSort;
    }

    public String getIsSuspected() {
        return isSuspected;
    }

    public void setIsSuspected(String isSuspected) {
        this.isSuspected = isSuspected;
    }

    public String getMorbidityDate() {
        return morbidityDate;
    }

    public void setMorbidityDate(String morbidityDate) {
        this.morbidityDate = morbidityDate;
    }

    public String getDiagnosticNote() {
        return diagnosticNote;
    }

    public void setDiagnosticNote(String diagnosticNote) {
        this.diagnosticNote = diagnosticNote;
    }

    public String getSubSyndrome() {
        return subSyndrome;
    }

    public void setSubSyndrome(String subSyndrome) {
        this.subSyndrome = subSyndrome;
    }
}
