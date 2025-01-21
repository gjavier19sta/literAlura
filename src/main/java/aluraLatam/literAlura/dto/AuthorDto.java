package aluraLatam.literAlura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorDto {

    private String name;

    @JsonAlias("birth_year")
    private int nacimiento;

    @JsonAlias("death_ywar")
    private int fallecimiento;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(int fallecimiento) {
        this.fallecimiento = fallecimiento;
    }
}
