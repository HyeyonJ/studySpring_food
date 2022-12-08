package example.sample.project.domain;

import lombok.Data;

@Data
public class Member {
	private Integer id;
	private String loginId;
	private String password;
	private String name;
}
