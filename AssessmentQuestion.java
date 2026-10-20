public class AssessmentQuestion {

    private int questionId;
    private int skillId;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctOption;

    public AssessmentQuestion(int questionId, int skillId,
                              String questionText,
                              String optionA,
                              String optionB,
                              String optionC,
                              String optionD,
                              String correctOption) {

        this.questionId = questionId;
        this.skillId = skillId;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getSkillId() {
        return skillId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }
}