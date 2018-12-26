package net.sourceforge.http.model;

import java.io.Serializable;
import java.util.List;

public class NodeModel implements Serializable {

    public NodeTypeEnum nodeType;

    public String node_name;

    public String node_url;

    public String node_net;

    public String node_des;

    public String is_def;

    public static class NodeModelResponse extends BaseResponse implements Serializable{

        public List<NodeModel> dev_net;

    }


}
