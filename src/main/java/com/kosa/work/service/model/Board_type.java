package com.kosa.work.service.model;

import com.kosa.work.service.model.general.GeneralModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board_type implements GeneralModel {
	private static final long serialVersionUID = 2358755265409616569L;
	private int board_code;
	private String board_name;

}
