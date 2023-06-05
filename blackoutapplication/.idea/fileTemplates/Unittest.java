#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import org.junit.jupiter.api.Test;

#parse("File Header.java")
public class ${NAME} {
}