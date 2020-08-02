package com.cesske.mps.model.entity.user;

import java.sql.Timestamp;
import javax.persistence.*;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User
 */
@Entity
@Table(name = "t_user")
@Data
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 //本条记录的唯一标识，主键
    @Column(name = "r_id")
	private Long rid;

	//密码
	@ApiModelProperty(value = "密码")
	@Column(name = "password")
	private String password;

	// 微信openId
	@ApiModelProperty(value = "微信openId")
	@Column(name = "openid")
	private String openid;

	 //昵称
	 @ApiModelProperty(value = "昵称")
    @Column(name = "nick_name")
	private String nickName;

	 //真实姓名
	@ApiModelProperty(value = "姓名")
    @Column(name = "name")
	private String name;

	 //性别
	@ApiModelProperty(value = "性别")
    @Column(name = "gender")
	private String gender;

	 //1 身份证
	 @ApiModelProperty(value = "证件类型")
    @Column(name = "id_card_type")
	private Integer idCardType;

	 //证件号码
	 @ApiModelProperty(value = "证件号码")
    @Column(name = "id_card_no")
	private String idCardNo;

	 //手机号
	@ApiModelProperty(value = "手机号")
    @Column(name = "mobile")
	private String mobile;

	 //用户类型，0.整车客户1.预售客户
	 @ApiModelProperty(value = "用户类型，0.整车客户1.预售客户")
    @Column(name = "type")
	private Integer type;

	 //0 无效 1 有效（默认值）
	 @ApiModelProperty(value = "状态0 无效 1 有效")
    @Column(name = "status")
	private Integer status;

    // 收货人
	@ApiModelProperty(value = "收货人")
    @Column(name = "consignee")
    private String consignee;

	// 收货人手机号
	@ApiModelProperty(value = "收货人手机号")
	@Column(name = "consignee_mobile")
	private String consigneeMobile;

	//省份编码
	@ApiModelProperty(value = "省份编码")
	@Column(name = "province")
	private Integer province;

	// 省份名称
	@ApiModelProperty(value = "省份名称")
	@Column(name = "province_name")
	private String provinceName;

	//城市编码
	@ApiModelProperty(value = "城市编码")
	@Column(name = "city")
	private Integer city;

	// 城市名称
	@ApiModelProperty(value = "城市名称")
	@Column(name = "city_name")
	private String cityName;

	// 区域编码
	@ApiModelProperty(value = "区域编码")
	@Column(name = "county")
	private Integer county;

	// 区域名称
	@ApiModelProperty(value = "区域名称")
	@Column(name = "county_name")
	private String countyName;

	//地址
	@ApiModelProperty(value = "详细地址")
	@Column(name = "address")
	private String address;

	//推荐人
	@ApiModelProperty(value = "推荐人")
	@Column(name = "referee")
	private String referee;

	//推荐人手机号
	@ApiModelProperty(value = "推荐人手机号")
	@Column(name = "referee_mobile")
	private String refereeMobile;

	// 大客户代码
	@ApiModelProperty(value = "大客户代码")
	@Column(name = "big_customer_code")
	private String bigCustomerCode;

	//创建时间
	@ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
	private Timestamp createTime;

    //更新时间
	@ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
	private Timestamp updateTime;

}
