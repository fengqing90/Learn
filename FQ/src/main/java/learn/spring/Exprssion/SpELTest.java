package learn. spring.Exprssion;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpELTest {

//	@Test
    public void testParserContext() {
        ExpressionParser parser = new SpelExpressionParser();
        ParserContext parserContext = new ParserContext() {
            @Override
            public boolean isTemplate() {
                return true;
            }

            @Override
            public String getExpressionPrefix() {
                return "#{";
            }

            @Override
            public String getExpressionSuffix() {
                return "}";
            }
        };
        String template = "#{'Hello '}#{'World!'}";
        Expression expression = parser.parseExpression(template, parserContext);
        Assert.assertEquals("Hello World!", expression.getValue());
        // /////////////////
        String str1 = parser.parseExpression("'Hello World!'").getValue(
            String.class);
        String str2 = parser.parseExpression("\"Hello World!\"").getValue(
            String.class);
        System.out.println(str1);
        System.out.println(str2);
        int result1 = parser.parseExpression("1+2-3*4/2").getValue(
            Integer.class);// -3
        System.out.println(result1);
        System.out.println(parser.parseExpression("'Hello' + ' World!'")
            .getValue(String.class));
        System.out.println(parser.parseExpression("'Hello' + ' World!'[1]")
            .getValue(String.class));
    }

    @Test
    public void testClassTypeExpression() {
        ExpressionParser parser = new SpelExpressionParser();
        // java.lang包类访问
        Class<String> result1 = parser.parseExpression("T(String)").getValue(
            Class.class);
        Assert.assertEquals(String.class, result1);
        // 其他包类访问
        String expression2 = "T(spring.Exprssion.SpELTest)";
        Class<String> result2 = parser.parseExpression(expression2).getValue(
            Class.class);
        Assert.assertEquals(SpELTest.class, result2);
        // 类静态字段访问
        int result3 = parser.parseExpression("T(Integer).MAX_VALUE").getValue(
            int.class);
        Assert.assertEquals(Integer.MAX_VALUE, result3);
        // 类静态方法调用
        int result4 = parser.parseExpression("T(Integer).parseInt('1')")
            .getValue(int.class);
        Assert.assertEquals(1, result4);
    }
}
