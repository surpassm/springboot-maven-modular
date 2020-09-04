package com.ysytech.tourism.common.call;


import lombok.*;

/**
 * @author mc
 * Create date 2019/1/4 11:32
 * Version 1.0
 * Description
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurpassmFile {

	/**
	 * 文件原名称
	 */
	private String fileOldName;
	/**
	 * 文件原名称
	 */
	private String fileNewName;
	/**
	 * 文件后缀
	 */
	private String fileSuffix;
	/**
	 * 文件路径
	 */
	private String url;

}
