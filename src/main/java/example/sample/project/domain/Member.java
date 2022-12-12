package example.sample.project.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Member {
	private Integer id;
	private String loginId;
	private String password;
	private String name;
	
	public Member(Integer id, String loginId, String password, String name) {
		super();
		this.id = id;
		this.loginId = loginId;
		this.password = password;
		this.name = name;
	}
}
