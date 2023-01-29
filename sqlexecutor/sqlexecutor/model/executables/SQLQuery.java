package io.blacktoast.utils.sqlexecutor.model.executables;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class SQLQuery extends SQLExecutable {

	private String query;

	public SQLQuery(String query) {
		super(new ArrayList<>());
		this.query = query;
	}

	@Builder
	public SQLQuery(List<String> resultColumns, String query) {
		super(resultColumns);
		this.query = query;
	}

}
