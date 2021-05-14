/**
 *
 */
package SmallApp.BaseUtil;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author cuixh
 */
public class XmlUtils {


    /**
     * @param
     * @throws IOException
     */
    public static Document setFsIosMobileRequestXML(JSONObject requestdata) {
        Document result = DocumentHelper.createDocument();
        Element fhe = result.addElement("FHE");
        Element postid = fhe.addElement("PostId");
        postid.setText("9441072");
        Element data = fhe.addElement("Data");
        data.addAttribute("DataType", "Json/P");
        data.setText(requestdata.toJSONString());
        return result;
    }


    /**
     * @param
     * @throws IOException
     */
    public static Document setFsMobileRequestXML(JSONObject requestdata) {
        Document result = DocumentHelper.createDocument();
        Element fhe = result.addElement("FHE");
        Element tickets = fhe.addElement("Tickets");
        Element fstickets1 = tickets.addElement("FSTicket");
        fstickets1.addAttribute("N", "");
        fstickets1.addAttribute("V", "");
        fstickets1.addAttribute("EV", "");
        Element fstickets2 = tickets.addElement("FSTicket");
        fstickets2.addAttribute("N", "");
        fstickets2.addAttribute("V", "");
        fstickets2.addAttribute("EV", "");
        Element postid = fhe.addElement("PostId");
        postid.setText(new Date().getTime() + "ad88819281888819212");
        Element data = fhe.addElement("Data");
        data.addAttribute("DataType", "Json/P");
        data.setText(requestdata.toJSONString());
        return result;
    }

    public static String doc2String(Document document) {
        String result = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = new OutputFormat();
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            result = out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        // TODO Auto-generated method stub
        JSONObject json = null;
        String str = "";
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("M4", "");
        data.put("M1", "18210911798");
        data.put("M3", "7JY3/wMOw9T5Xtvw9NCcfLyOFAGROlpTv2YhICFJT0qeJZzDd8mdo001Uxdo/rNflwmpcc6BT+RG\nslL6POOWMyK7YOXl4WfwCU6NW+b0eV2WV7k5POhN+0lBGI8UMa01cLxwkMVrTcZY3kopehECoa8k\nsMhV8+0duJbHBNsp9X5PaJfkSjDLGV0j7kTwBIbHLqXNZ5E6hjQT8knybD1EhnmYGvbwZWP9xSsF\nhmsSoh5gb4XhKzpYFlANF25JPI+Zrue8ajPvPNtGWsf8K1i12exAAHjFwP3AFk0R+ZFXFABNsTCY\nnfNkiWsmsRhFMq4S0ZuY4TzI2z8kNKp3iPtuMw==\n");
        data.put("M5", "+86");
        data.put("M2", "");
        json = HttpUtils.transformToJson(data);
        Document doc = setFsMobileRequestXML(json);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = new OutputFormat();
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(doc);
            str = out.toString();
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("fsticket is: " + getElementAttributeValue(str, "FSTicket", "EV"));
        System.out.println("post id is : " + getElementText(str, "PostId"));
    }

    public static String mapToXml(HashMap<?, ?> map) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("node");
        for (Object obj : map.keySet()) {
            Element keyElement = nodeElement.addElement("key");
            keyElement.addAttribute("label", String.valueOf(obj));
            keyElement.setText(String.valueOf(map.get(obj)));
        }
        return doc2String(document);
    }

    public static String getElementAttributeValue(String xmlString, String elementName, String attributeName) throws DocumentException {
        String result = "";
        SAXReader reader = new SAXReader();
        @SuppressWarnings("deprecation")
        //Document document = reader.read(new StringBufferInputStream(xmlString));
                Document document = DocumentHelper.parseText(xmlString);
        Element root = document.getRootElement();
        @SuppressWarnings("rawtypes")
        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            if (element.getName().equals(elementName)) {
                result = element.attributeValue(attributeName);
            }
        }
        return result;
    }


    public static String getElementTextByXpath(String xpath, String xmlString) {
        Node node = null;
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = DocumentHelper.parseText(xmlString);
            node = document.selectSingleNode(xpath);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//		System.out.println(">>>>>>>xpath:"+xpath);
//		System.out.println(">>>>>"+node.getText());
        String text = "";
        if (null != node) {
            text = node.getText();
        }
        return text;
    }


    public static String getElementText(String xmlString, String elementName) {
        String result = "";
        SAXReader reader = new SAXReader();
        //Document document = reader.read(new StringBufferInputStream(xmlString));
        Document document;
        try {
            document = DocumentHelper.parseText(xmlString);
            Element root = document.getRootElement();
            Iterator it = root.elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                if (element.getName().equals(elementName)) {
                    result = element.getText();
                }
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String getMobileRequestData(JSONObject json) {
        String result = "";
        result = doc2String(setFsMobileRequestXML(json));
        return result;

    }
}
