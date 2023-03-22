package com.project.BingoApi.jpa.config;

import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MysqlCustomDialect extends MySQL5Dialect {

    public MysqlCustomDialect() {
        super();
        this.registerFunction(
                "ST_Distance_Sphere",
                new StandardSQLFunction("ST_Distance_Sphere", StandardBasicTypes.STRING)
        );

        this.registerFunction(
                "POINT",
                new StandardSQLFunction("POINT", StandardBasicTypes.STRING)
        );
    }


}
