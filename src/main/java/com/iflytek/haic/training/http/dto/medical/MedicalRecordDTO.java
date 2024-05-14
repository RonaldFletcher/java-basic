package com.iflytek.haic.training.http.dto.medical;

import java.util.List;

/**
 * 病例数据
 */
public class MedicalRecordDTO {
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别
     */
    private String sex;

    /**
     * 主诉（主诉、现病史、既往史、辅助检查、体格检查、诊断信息不可同时为空）
     */
    private String mainSuit;

    /**
     * 现病史
     */
    private String illnessHistory;

    /**
     * 既往史
     */
    private String previousHistory;

    /**
     * 辅助检查
     */
    private String auxExam;

    /**
     * 体格检查
     */
    private String checkup;

    /**
     * 舌象
     */
    private String tongue;

    /**
     * 脉象
     */
    private String pulse;

    /**
     * 病人id
     */
    private String patientId;

    /**
     * 病历id
     */
    private String medicalId;

    /**
     * 医生id
     */
    private String doctorId;

    /**
     * 机构id
     */
    private String orgId;

    /**
     * 过敏史
     */
    private String allergyHistory;

    /**
     * 个人史
     */
    private String personalHistory;

    /**
     * 家族史
     */
    private String familyHistory;

    /**
     * 复检（1：复检；0：初检）
     */
    private String reexamine;

    /**
     * 急诊（急诊字段，1：是；0：否）
     */
    private String emergency;

    /**
     * 诊断列表
     */
    private List<DiagnosisDTO> diagList;

    /**
     * 是否是最终状态（1:是; 0:否）
     */
    private String isFinalState;

    /**
     * 事件id
     */
    private String eventId;

    /**
     * 医院名称
     */
    private String orgName;

    /**
     * 市code-分库分表预留字段
     */
    private String cityCode;

    /**
     * 区code-分库分表预留字段
     */
    private String districtCode;

    /**
     * 数据产品形态 1:辅诊 2:工作站 3:基层一体化
     */
    private String productSource;

    /**
     * 数据来源，1、建档注册；2、门诊服务；3、住院服务；4、健康体检；5、计划免疫；6、健康随访；7、公卫报卡；9、其它；
     */
    private String dataSrc;

    /**
     * 病历质检场景类型（1：西医；2：中医；3：中西医）
     */
    private String recordTypeCode;

    /**
     * 病历标识（1：就诊；2：体检；9：其他）
     */
    private String basicMedSign;

    /**
     * 区划代码
     */
    private String stateCode;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 患者就诊时间（yyyy-mm-dd HH:MM:SS）
     */
    private String clinicalTime;

    /**
     * 病人唯一标识（如身份证号，需保证全局唯一）
     */
    private String patientIndex;

    /**
     * 历史病历标识 0: 历史病历仅让结合使用，不用获取诊断 1: 当前病历，需要获取诊断
     */
    private String diagnoseFlag;

    /**
     * 历史病历组ID
     */
    private String historyMedicalGroupId;

    public String getMainSuit() {
        return mainSuit;
    }

    public void setMainSuit(String mainSuit) {
        this.mainSuit = mainSuit;
    }

    public String getIllnessHistory() {
        return illnessHistory;
    }

    public void setIllnessHistory(String illnessHistory) {
        this.illnessHistory = illnessHistory;
    }

    public String getPreviousHistory() {
        return previousHistory;
    }

    public void setPreviousHistory(String previousHistory) {
        this.previousHistory = previousHistory;
    }

    public String getAuxExam() {
        return auxExam;
    }

    public void setAuxExam(String auxExam) {
        this.auxExam = auxExam;
    }

    public String getCheckup() {
        return checkup;
    }

    public void setCheckup(String checkup) {
        this.checkup = checkup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTongue() {
        return tongue;
    }

    public void setTongue(String tongue) {
        this.tongue = tongue;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAllergyHistory() {
        return allergyHistory;
    }

    public void setAllergyHistory(String allergyHistory) {
        this.allergyHistory = allergyHistory;
    }

    public String getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(String personalHistory) {
        this.personalHistory = personalHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getReexamine() {
        return reexamine;
    }

    public void setReexamine(String reexamine) {
        this.reexamine = reexamine;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public List<DiagnosisDTO> getDiagList() {
        return diagList;
    }

    public void setDiagList(List<DiagnosisDTO> diagList) {
        this.diagList = diagList;
    }

    public String getIsFinalState() {
        return isFinalState;
    }

    public void setIsFinalState(String isFinalState) {
        this.isFinalState = isFinalState;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getProductSource() {
        return productSource;
    }

    public void setProductSource(String productSource) {
        this.productSource = productSource;
    }

    public String getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }

    public String getRecordTypeCode() {
        return recordTypeCode;
    }

    public void setRecordTypeCode(String recordTypeCode) {
        this.recordTypeCode = recordTypeCode;
    }

    public String getBasicMedSign() {
        return basicMedSign;
    }

    public void setBasicMedSign(String basicMedSign) {
        this.basicMedSign = basicMedSign;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getClinicalTime() {
        return clinicalTime;
    }

    public void setClinicalTime(String clinicalTime) {
        this.clinicalTime = clinicalTime;
    }

    public String getPatientIndex() {
        return patientIndex;
    }

    public void setPatientIndex(String patientIndex) {
        this.patientIndex = patientIndex;
    }

    public String getDiagnoseFlag() {
        return diagnoseFlag;
    }

    public void setDiagnoseFlag(String diagnoseFlag) {
        this.diagnoseFlag = diagnoseFlag;
    }

    public String getHistoryMedicalGroupId() {
        return historyMedicalGroupId;
    }

    public void setHistoryMedicalGroupId(String historyMedicalGroupId) {
        this.historyMedicalGroupId = historyMedicalGroupId;
    }
}
