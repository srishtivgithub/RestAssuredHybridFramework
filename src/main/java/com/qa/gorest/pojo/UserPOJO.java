package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // doesnt creates json with parameters whose value=null
public class UserPOJO {

	private String name;
	private String email;
	private String gender;
	private String status;
}
