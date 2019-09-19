package com.java.butterfly.business.test.entity;

public class TempTest {
    private String testId;
    
    private String testName;
    
    private Long testAge;
    
    public String getTestId() {
        return testId;
    }
    
    public void setTestId(String testId) {
        this.testId = testId == null ? null : testId.trim();
    }
    
    public String getTestName() {
        return testName;
    }
    
    public void setTestName(String testName) {
        this.testName = testName == null ? null : testName.trim();
    }
    
    public Long getTestAge() {
        return testAge;
    }
    
    public void setTestAge(Long testAge) {
        this.testAge = testAge;
    }
}