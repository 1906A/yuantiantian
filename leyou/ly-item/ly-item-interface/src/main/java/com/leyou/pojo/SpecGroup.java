package com.leyou.pojo;

import javax.persistence.Table;
import java.util.List;

@Table(name = "tb_spec_group")
public class SpecGroup {

    private Long id;
    private Long cid;
    private String name;
    private List<SpecParam> params;

    public List<SpecParam> getParams() {
        return params;
    }

    public void setParams(List<SpecParam> params) {
        this.params = params;
    }

    public SpecGroup(Long id, Long cid, String name) {
        this.id = id;
        this.cid = cid;
        this.name = name;
    }

    public SpecGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
