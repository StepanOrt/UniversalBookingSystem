package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.pk.ResourcePropertyValueId;
 
 
@Entity
@Table(name = "resource_property_value")
@AssociationOverrides({
        @AssociationOverride(name = "pk.resource", 
            joinColumns = @JoinColumn(name = "resource_id")),
        @AssociationOverride(name = "pk.property", 
            joinColumns = @JoinColumn(name = "property_id")) })
public class ResourcePropertyValue implements Serializable {
     
    private ResourcePropertyValueId pk = new ResourcePropertyValueId();
    private String value;
     
    public ResourcePropertyValue() {}
     
    @EmbeddedId
    public ResourcePropertyValueId getPk() {
        return pk;
    }
     
    public void setPk(ResourcePropertyValueId pk) {
        this.pk = pk;
    }
 
    @Transient
    public Resource getResource() {
        return getPk().getResource();
    }
     
    public void setResource(Resource resource) {
        getPk().setResource(resource);
    }
     
    @Transient
    public ResourceProperty getProperty() {
        return getPk().getProperty();
    }
     
    public void setProperty(ResourceProperty property) {
        getPk().setProperty(property);
    }
     
    @Column(name = "value")
    public String getValue() {
        return this.value;
    }
     
    public void setValue(String value) {
        this.value = value;
    }
     
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
  
        ResourcePropertyValue that = (ResourcePropertyValue) o;
  
        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;
  
        return true;
    }
  
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}