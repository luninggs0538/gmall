package com.atguigu.gmall.hive;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LogUDTF extends GenericUDTF {


    @Override
    public void close() throws HiveException {

    }

    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        List<? extends StructField> allStructFieldRefs = argOIs.getAllStructFieldRefs();
        if (allStructFieldRefs.size() != 1) {
            throw new UDFArgumentException("参数个数只能为1");
        }
        if(!"string".equals(allStructFieldRefs.get(0).getFieldObjectInspector().getTypeName())){
            throw new UDFArgumentException("参数类型只能为string");
        }


        ArrayList<String> fieldNames = new ArrayList<String>();
        ArrayList<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        fieldNames.add("event_name");
        fieldNames.add("event_json");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,
                fieldOIs);
    }

    @Override
    public void process(Object[] args) throws HiveException {

        String events = args[0].toString();

        try {
            JSONArray jsonArray = new JSONArray(events);
            for (int i = 0; i < jsonArray.length(); i++) {
                String[] result = new String[2];

                result[0]=jsonArray.getJSONObject(i).getString("en");
                result[1]=jsonArray.getString(i);

                forward(result);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
