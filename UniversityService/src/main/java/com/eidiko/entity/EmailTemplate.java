package com.eidiko.entity;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Email_Template")
public class EmailTemplate {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nameOfTemplate;
	private String subject;

    @Lob
    private Clob body;
    
    
    public String getBody() {
        if (body == null) {
            return null;
        }
        try {
            Reader reader = body.getCharacterStream();
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer, 0, buffer.length)) != -1) {
                stringBuilder.append(buffer, 0, bytesRead);
            }
            return stringBuilder.toString();
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to convert CLOB to String", e);
        }
    }

    // Custom setter for body to convert String to Clob
    public void setBody(Clob body) {
        this.body = body;
    }
}
