/*
 * 《审核阶段标准值》
 *01 个人申报
 *10 单位审核
 *11 申报材料审核（单位）
 *12 申报材料审核（人事处）
 *13 申报材料审核（职能部门）
 *21 单位评议（推荐名单）
 *22 单位评议（考核结果）
 *23 单位评议（拟聘结果）
 *31 资格审查
 *41 同行专家评审
 *51 学科评议
 *61 学校评议
 *71 学校审议
 *91 聘岗处理*/
var auditPhase = {
	PERSONAL_APPLY:'01',
	DEPT_VERIFY:'10',
	DEPT_VERIFY_STUFF:'11',
	STAFF_VERIFY_STUFF:'12',
	ORG_VERIFY_STUFF:'13',
	ASSESS_RECOMMEND:'21',
	ASSESS_EXAM:'22',
	ASSESS_EMPLOYEE:'23',
	DEPT_PARTY:'24',
	QUA_CHECK:'31',
	EXPERT_ASSESS:'41',
	SUBJECT_ASSESS:'51',
	SCHOOL_ASSESS:'61',
	SCHOOL_REVIEW:'71',
	EMPLOYEE:'91'
}

/**
 * 《审核状态||审核结果》
 */
var auditStatus = {
	DOING:'0',
	PASS:'1',
	NO_PASS:'2',
	BACK:'3',
	SUBMIT:'4'
}

/**
 * 《业务类型》
 * 1.职称评聘
 * 2.聘期考核及岗位分级
 * 3.职员晋升
 * 4.工勤聘岗
 */
var busSort = {
	TECHNIC_ASSESS:"1",
	TERM_EXAM:"3",
	STAFF_PROMOTION:"4",
	WORKER_ENGAGE:"5"
}

/***
 * 常量
 */
var constant = {
	YES:'1',
	NO:'0'
}

