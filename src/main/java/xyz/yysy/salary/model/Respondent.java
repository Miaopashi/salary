package xyz.yysy.salary.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@Entity
public class Respondent {
    @Id
    private final String id;
    private final double salary;
    private final String gender;
    private final double collegeGPA;
    private final double percentage12;  // 十二年级平均分
    private final double english;
    private final double logical;
    private final double quant;  // 定量能力
    private final double conscientiousness;  // 严谨性
    private final double agreeableness;  // 宜人性
    private final double extraversion;  // 外向性
    private final double nueroticism;  // 神经质人格特质
    private final double openness;  // 开放性
}
