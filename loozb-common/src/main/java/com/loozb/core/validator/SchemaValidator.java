package com.loozb.core.validator;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * json验证
 * @Author： 龙召碧
 * @Date: Created in 2017-3-3 19:33
 */
public class SchemaValidator {
    private static Log log = LogFactory.getLog(SchemaValidator.class);

    private final static JsonSchemaFactory factory = JsonSchemaFactory
            .byDefault();

    public static void validate(Object object, Errors errors, String name) {
        String schemaPath = "D:\\schema\\" + name.toLowerCase() + ".json";
        String jsonData = JSONObject.toJSONString(object);
        StringBuffer msg = new StringBuffer();
        try {
            ProcessingReport report = SchemaValidator.validatorSchema(
                    schemaPath, jsonData);
            if (report.isSuccess()) {
                errors.setHasErrors(false);
            } else {
                for (Iterator<ProcessingMessage> iterator = report.iterator(); iterator
                        .hasNext();) {
                    ProcessingMessage o = (ProcessingMessage) iterator.next();
                    JsonNode jn = o.asJson();
                    String pointer = jn.get("instance").get("pointer").asText()
                            .replace("/", "");
                    String message = jn.get("message").asText();
                    msg.append("field:" + pointer + "," + message + "<br/>");
                }
                errors.setHasErrors(true);
                errors.setMsg(msg.toString());
            }
        } catch (IOException e) {
            errors.setHasErrors(true);
            errors.setMsg("出现已捕获的验证异常，请检查JSON路径问题");
            e.printStackTrace();
        } catch (ProcessingException e) {
            errors.setHasErrors(true);
            errors.setMsg("出现已捕获的验证异常，请检查JSON路径问题");
            e.printStackTrace();
        }

    }

    public static ProcessingReport validatorSchema(String schemaPath,
                                                   String jsonData) throws IOException, ProcessingException {
        JsonNode schemaNode = SchemaValidator.readJSONfile(schemaPath);
        JsonNode instance = JsonLoader.fromString(jsonData);
        JsonSchema schema = factory.getJsonSchema(schemaNode);
        ProcessingReport processingReport = schema.validate(instance);
        return processingReport;
    }

    private static JsonNode readJSONfile(String filePath) {
        JsonNode instance = null;
        try {
            instance = new JsonNodeReader()
                    .fromReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static void main(String[] args) {
//        String schemaPath = "D:\\schema\\User.json";
//        Test1 t = new Test1();
//        t.setName("aasdfasdf");
//        t.setPhone("11111111");
//        String jsonData = JSONObject.toJSONString(t);
//
//        StringBuffer buffer = new StringBuffer();
//        try {
//            ProcessingReport report = SchemaValidator.validatorSchema(
//                    schemaPath, jsonData);
//            if (report.isSuccess()) {
//                System.out.println("验证成功");
//            } else {
//                for (Iterator<ProcessingMessage> iterator = report.iterator(); iterator
//                        .hasNext();) {
//                    ProcessingMessage o = (ProcessingMessage) iterator.next();
//                    JsonNode jn = o.asJson();
//                    String pointer = jn.get("instance").get("pointer").asText()
//                            .replace("/", "");
//                    String message = jn.get("message").asText();
//                    buffer.append("field:" + pointer + "," + message + "\n");
//                }
//            }
//            System.out.println(buffer.toString());
//        } catch (IOException e) {
//        } catch (ProcessingException e) {
//            e.printStackTrace();
//        }
    }
}
