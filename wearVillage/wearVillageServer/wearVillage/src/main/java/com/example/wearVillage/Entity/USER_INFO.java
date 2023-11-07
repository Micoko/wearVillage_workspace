package com.example.wearVillage.Entity;


import com.example.wearVillage.DTO.USER_INFO_DTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class USER_INFO {
    @JsonProperty("ID")
    @Id
    private String ID;
    @JsonProperty("PW")
    private String PW;
    @JsonProperty("NICKNAME")
    private String NICKNAME;
    @JsonProperty("EMAIL")
    private String EMAIL;
    @JsonProperty("PROFILEIMG")
    private String PROFILEIMG;
    @JsonProperty("THEME")
    private String THEME;
    @JsonProperty("GENDER")
    private String GENDER;
    @JsonProperty("BIRTH")
    private String BIRTH;
    USER_INFO(USER_INFO_DTO userInfoDto){
        this.ID = userInfoDto.getID();
        this.PW = userInfoDto.getPW();
        this.NICKNAME = userInfoDto.getNICKNAME();
        this.EMAIL = userInfoDto.getEMAIL();
        this.PROFILEIMG = userInfoDto.getPROFILEIMG();
        this.THEME = userInfoDto.getTHEME();
        this.GENDER = userInfoDto.getGENDER();
        this.BIRTH = userInfoDto.getBIRTH();
    }
    @Override
    public String toString(){
        return
                "아이디="+ ID +
                "비밀번호="+ PW +
                "이메일" +EMAIL +
                "닉네임" +NICKNAME +
                "프로필사진" +PROFILEIMG +
                "성별" +GENDER +
                "생일"+BIRTH+
                "테마"+THEME;
    }


}
