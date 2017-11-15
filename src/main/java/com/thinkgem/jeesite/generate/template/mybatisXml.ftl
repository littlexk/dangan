<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${moduleName}.dao.${subModuleName}.${ClassName}Dao">
    <select id="list" parameterType="map" resultType="upperCaseKeyMap">
        select t.* from ${tableName} t
    </select>

    <insert id="insert" parameterType="map">
        insert into  ${tableName} (
        <trim prefix=""  suffixOverrides=",">
			${insertCol}
        </trim>
        )values(
        <trim prefix=""  suffixOverrides=",">
			${insertColValue}
        </trim>
        )
    </insert>
    <select id="get" parameterType="string" resultType="upperCaseKeyMap">
        SELECT t.* FROM  ${tableName} t where t.${priColName}=${getColParams}
    </select>

    <update id="update"  parameterType="map">
        update  ${tableName}
        <set>
            ${updateCol}
        </set>
        where ${priColName} = ${priColNameParams}
    </update>
    <delete id="delete" parameterType="map">
        DELETE FROM ${tableName}
        <where>
            AND ${priColName} IN
            <foreach item="item" index="index" collection="ids" open="(" separator="," close=")">
            ${r"#{item}"}
            </foreach>
        </where>
    </delete>
</mapper>