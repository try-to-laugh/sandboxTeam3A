## How work with database
<hr>
<ul>
    <li><strong>Initialize database:</strong> mvn liquibase:update -pl release-db -Dliquibase.changeLogFile=db_changelog_master.xml -Dliquibase.url=jdbc:postgresql://localhost:5432/[schema] -Dliquibase.username=[username] -Dliquibase.password=[password] </li>
    <li><strong>Rollback changes:</strong> mvn liquibase:rollback -pl release-db -Dliquibase.changeLogFile=db_changelog_master.xml -Dliquibase.rollbackCount=3 -Dliquibase.url=jdbc:postgresql://localhost:5432/[schema] -Dliquibase.username=[username] -Dliquibase.password=[password]</li>
</ul>
