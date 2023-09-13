package com.kosa.work.service.model;

import java.util.Date;

import com.kosa.work.service.model.general.GeneralModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO implements GeneralModel {
	private static final long serialVersionUID = -9062464033624231460L;
	private int comment_num;
	private String mem_id;
	private int board_num;
	private String detail;
	private String reg_date;
	
}
