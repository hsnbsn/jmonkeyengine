/**
 * animationType.java This file was generated by XMLSpy 2006sp2 Enterprise
 * Edition. YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE OVERWRITTEN WHEN
 * YOU RE-RUN CODE GENERATION. Refer to the XMLSpy Documentation for further
 * details. http://www.altova.com/xmlspy
 */

package com.jmex.model.collada.schema;

import com.jmex.model.collada.types.SchemaID;
import com.jmex.model.collada.types.SchemaNCName;

public class animationType extends com.jmex.model.collada.xml.Node {

    private static final long serialVersionUID = 1L;

    public animationType(animationType node) {
        super(node);
    }

    public animationType(org.w3c.dom.Node node) {
        super(node);
    }

    public animationType(org.w3c.dom.Document doc) {
        super(doc);
    }

    public animationType(com.jmex.model.collada.xml.Document doc,
            String namespaceURI, String prefix, String name) {
        super(doc, namespaceURI, prefix, name);
    }

    public void adjustPrefix() {
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Attribute, null, "id"); tmpNode != null; tmpNode = getDomNextChild(
                Attribute, null, "id", tmpNode)) {
            internalAdjustPrefix(tmpNode, false);
        }
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Attribute, null,
                "name"); tmpNode != null; tmpNode = getDomNextChild(Attribute,
                null, "name", tmpNode)) {
            internalAdjustPrefix(tmpNode, false);
        }
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "asset"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "asset", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
            new assetType(tmpNode).adjustPrefix();
        }
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "source"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "source", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
            new sourceType(tmpNode).adjustPrefix();
        }
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "sampler"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "sampler", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
            new samplerType(tmpNode).adjustPrefix();
        }
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "channel"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "channel", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
            new channelType(tmpNode).adjustPrefix();
        }
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "animation"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "animation", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
            new animationType(tmpNode).adjustPrefix();
        }
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "extra"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "extra", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
            new extraType(tmpNode).adjustPrefix();
        }
    }

    public static int getidMinCount() {
        return 0;
    }

    public static int getidMaxCount() {
        return 1;
    }

    public int getidCount() {
        return getDomChildCount(Attribute, null, "id");
    }

    public boolean hasid() {
        return hasDomChild(Attribute, null, "id");
    }

    public SchemaID newid() {
        return new SchemaID();
    }

    public SchemaID getidAt(int index) throws Exception {
        return new SchemaID(getDomNodeValue(dereference(getDomChildAt(
                Attribute, null, "id", index))));
    }

    public org.w3c.dom.Node getStartingidCursor() throws Exception {
        return getDomFirstChild(Attribute, null, "id");
    }

    public org.w3c.dom.Node getAdvancedidCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Attribute, null, "id", curNode);
    }

    public SchemaID getidValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new SchemaID(getDomNodeValue(dereference(curNode)));
    }

    public SchemaID getid() throws Exception {
        return getidAt(0);
    }

    public void removeidAt(int index) {
        removeDomChildAt(Attribute, null, "id", index);
    }

    public void removeid() {
        while (hasid())
            removeidAt(0);
    }

    public void addid(SchemaID value) {
        if (value.isNull() == false) {
            appendDomChild(Attribute, null, "id", value.toString());
        }
    }

    public void addid(String value) throws Exception {
        addid(new SchemaID(value));
    }

    public void insertidAt(SchemaID value, int index) {
        insertDomChildAt(Attribute, null, "id", index, value.toString());
    }

    public void insertidAt(String value, int index) throws Exception {
        insertidAt(new SchemaID(value), index);
    }

    public void replaceidAt(SchemaID value, int index) {
        replaceDomChildAt(Attribute, null, "id", index, value.toString());
    }

    public void replaceidAt(String value, int index) throws Exception {
        replaceidAt(new SchemaID(value), index);
    }

    public static int getnameMinCount() {
        return 0;
    }

    public static int getnameMaxCount() {
        return 1;
    }

    public int getnameCount() {
        return getDomChildCount(Attribute, null, "name");
    }

    public boolean hasname() {
        return hasDomChild(Attribute, null, "name");
    }

    public SchemaNCName newname() {
        return new SchemaNCName();
    }

    public SchemaNCName getnameAt(int index) throws Exception {
        return new SchemaNCName(getDomNodeValue(dereference(getDomChildAt(
                Attribute, null, "name", index))));
    }

    public org.w3c.dom.Node getStartingnameCursor() throws Exception {
        return getDomFirstChild(Attribute, null, "name");
    }

    public org.w3c.dom.Node getAdvancednameCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Attribute, null, "name", curNode);
    }

    public SchemaNCName getnameValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new SchemaNCName(getDomNodeValue(dereference(curNode)));
    }

    public SchemaNCName getname() throws Exception {
        return getnameAt(0);
    }

    public void removenameAt(int index) {
        removeDomChildAt(Attribute, null, "name", index);
    }

    public void removename() {
        while (hasname())
            removenameAt(0);
    }

    public void addname(SchemaNCName value) {
        if (value.isNull() == false) {
            appendDomChild(Attribute, null, "name", value.toString());
        }
    }

    public void addname(String value) throws Exception {
        addname(new SchemaNCName(value));
    }

    public void insertnameAt(SchemaNCName value, int index) {
        insertDomChildAt(Attribute, null, "name", index, value.toString());
    }

    public void insertnameAt(String value, int index) throws Exception {
        insertnameAt(new SchemaNCName(value), index);
    }

    public void replacenameAt(SchemaNCName value, int index) {
        replaceDomChildAt(Attribute, null, "name", index, value.toString());
    }

    public void replacenameAt(String value, int index) throws Exception {
        replacenameAt(new SchemaNCName(value), index);
    }

    public static int getassetMinCount() {
        return 0;
    }

    public static int getassetMaxCount() {
        return 1;
    }

    public int getassetCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "asset");
    }

    public boolean hasasset() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "asset");
    }

    public assetType newasset() {
        return new assetType(domNode.getOwnerDocument().createElementNS(
                "http://www.collada.org/2005/11/COLLADASchema", "asset"));
    }

    public assetType getassetAt(int index) throws Exception {
        return new assetType(
                dereference(getDomChildAt(Element,
                        "http://www.collada.org/2005/11/COLLADASchema",
                        "asset", index)));
    }

    public org.w3c.dom.Node getStartingassetCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "asset");
    }

    public org.w3c.dom.Node getAdvancedassetCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "asset",
                curNode);
    }

    public assetType getassetValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new assetType(dereference(curNode));
    }

    public assetType getasset() throws Exception {
        return getassetAt(0);
    }

    public void removeassetAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "asset", index);
    }

    public void removeasset() {
        while (hasasset())
            removeassetAt(0);
    }

    public void addasset(assetType value) {
        appendDomElement("http://www.collada.org/2005/11/COLLADASchema",
                "asset", value);
    }

    public void insertassetAt(assetType value, int index) {
        insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "asset", index, value);
    }

    public void replaceassetAt(assetType value, int index) {
        replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "asset", index, value);
    }

    public static int getsourceMinCount() {
        return 1;
    }

    public static int getsourceMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getsourceCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "source");
    }

    public boolean hassource() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "source");
    }

    public sourceType newsource() {
        return new sourceType(domNode.getOwnerDocument().createElementNS(
                "http://www.collada.org/2005/11/COLLADASchema", "source"));
    }

    public sourceType getsourceAt(int index) throws Exception {
        return new sourceType(
                dereference(getDomChildAt(Element,
                        "http://www.collada.org/2005/11/COLLADASchema",
                        "source", index)));
    }

    public org.w3c.dom.Node getStartingsourceCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "source");
    }

    public org.w3c.dom.Node getAdvancedsourceCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "source",
                curNode);
    }

    public sourceType getsourceValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new sourceType(dereference(curNode));
    }

    public sourceType getsource() throws Exception {
        return getsourceAt(0);
    }

    public void removesourceAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "source", index);
    }

    public void removesource() {
        while (hassource())
            removesourceAt(0);
    }

    public void addsource(sourceType value) {
        appendDomElement("http://www.collada.org/2005/11/COLLADASchema",
                "source", value);
    }

    public void insertsourceAt(sourceType value, int index) {
        insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "source", index, value);
    }

    public void replacesourceAt(sourceType value, int index) {
        replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "source", index, value);
    }

    public static int getsamplerMinCount() {
        return 1;
    }

    public static int getsamplerMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getsamplerCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "sampler");
    }

    public boolean hassampler() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "sampler");
    }

    public samplerType newsampler() {
        return new samplerType(domNode.getOwnerDocument().createElementNS(
                "http://www.collada.org/2005/11/COLLADASchema", "sampler"));
    }

    public samplerType getsamplerAt(int index) throws Exception {
        return new samplerType(dereference(getDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "sampler",
                index)));
    }

    public org.w3c.dom.Node getStartingsamplerCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "sampler");
    }

    public org.w3c.dom.Node getAdvancedsamplerCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "sampler",
                curNode);
    }

    public samplerType getsamplerValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new samplerType(dereference(curNode));
    }

    public samplerType getsampler() throws Exception {
        return getsamplerAt(0);
    }

    public void removesamplerAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "sampler",
                index);
    }

    public void removesampler() {
        while (hassampler())
            removesamplerAt(0);
    }

    public void addsampler(samplerType value) {
        appendDomElement("http://www.collada.org/2005/11/COLLADASchema",
                "sampler", value);
    }

    public void insertsamplerAt(samplerType value, int index) {
        insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "sampler", index, value);
    }

    public void replacesamplerAt(samplerType value, int index) {
        replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "sampler", index, value);
    }

    public static int getchannelMinCount() {
        return 1;
    }

    public static int getchannelMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getchannelCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "channel");
    }

    public boolean haschannel() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "channel");
    }

    public channelType newchannel() {
        return new channelType(domNode.getOwnerDocument().createElementNS(
                "http://www.collada.org/2005/11/COLLADASchema", "channel"));
    }

    public channelType getchannelAt(int index) throws Exception {
        return new channelType(dereference(getDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "channel",
                index)));
    }

    public org.w3c.dom.Node getStartingchannelCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "channel");
    }

    public org.w3c.dom.Node getAdvancedchannelCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "channel",
                curNode);
    }

    public channelType getchannelValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new channelType(dereference(curNode));
    }

    public channelType getchannel() throws Exception {
        return getchannelAt(0);
    }

    public void removechannelAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "channel",
                index);
    }

    public void removechannel() {
        while (haschannel())
            removechannelAt(0);
    }

    public void addchannel(channelType value) {
        appendDomElement("http://www.collada.org/2005/11/COLLADASchema",
                "channel", value);
    }

    public void insertchannelAt(channelType value, int index) {
        insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "channel", index, value);
    }

    public void replacechannelAt(channelType value, int index) {
        replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "channel", index, value);
    }

    public static int getanimationMinCount() {
        return 0;
    }

    public static int getanimationMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getanimationCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "animation");
    }

    public boolean hasanimation() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "animation");
    }

    public animationType newanimation() {
        return new animationType(domNode.getOwnerDocument().createElementNS(
                "http://www.collada.org/2005/11/COLLADASchema", "animation"));
    }

    public animationType getanimationAt(int index) throws Exception {
        return new animationType(dereference(getDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "animation",
                index)));
    }

    public org.w3c.dom.Node getStartinganimationCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "animation");
    }

    public org.w3c.dom.Node getAdvancedanimationCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "animation",
                curNode);
    }

    public animationType getanimationValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new animationType(dereference(curNode));
    }

    public animationType getanimation() throws Exception {
        return getanimationAt(0);
    }

    public void removeanimationAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "animation",
                index);
    }

    public void removeanimation() {
        while (hasanimation())
            removeanimationAt(0);
    }

    public void addanimation(animationType value) {
        appendDomElement("http://www.collada.org/2005/11/COLLADASchema",
                "animation", value);
    }

    public void insertanimationAt(animationType value, int index) {
        insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "animation", index, value);
    }

    public void replaceanimationAt(animationType value, int index) {
        replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "animation", index, value);
    }

    public static int getextraMinCount() {
        return 0;
    }

    public static int getextraMaxCount() {
        return Integer.MAX_VALUE;
    }

    public int getextraCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "extra");
    }

    public boolean hasextra() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "extra");
    }

    public extraType newextra() {
        return new extraType(domNode.getOwnerDocument().createElementNS(
                "http://www.collada.org/2005/11/COLLADASchema", "extra"));
    }

    public extraType getextraAt(int index) throws Exception {
        return new extraType(
                dereference(getDomChildAt(Element,
                        "http://www.collada.org/2005/11/COLLADASchema",
                        "extra", index)));
    }

    public org.w3c.dom.Node getStartingextraCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "extra");
    }

    public org.w3c.dom.Node getAdvancedextraCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "extra",
                curNode);
    }

    public extraType getextraValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new extraType(dereference(curNode));
    }

    public extraType getextra() throws Exception {
        return getextraAt(0);
    }

    public void removeextraAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "extra", index);
    }

    public void removeextra() {
        while (hasextra())
            removeextraAt(0);
    }

    public void addextra(extraType value) {
        appendDomElement("http://www.collada.org/2005/11/COLLADASchema",
                "extra", value);
    }

    public void insertextraAt(extraType value, int index) {
        insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "extra", index, value);
    }

    public void replaceextraAt(extraType value, int index) {
        replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "extra", index, value);
    }

    private org.w3c.dom.Node dereference(org.w3c.dom.Node node) {
        return node;
    }
}