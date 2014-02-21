package cz.cvut.fit.ortstepa.universalbookingsystem.domain.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.Resource;
import cz.cvut.fit.ortstepa.universalbookingsystem.domain.ResourceProperty;
  
@Embeddable
public class ResourcePropertyValueId implements Serializable {
 
    private Resource resource;
    private ResourceProperty property;
     
    @ManyToOne(fetch=FetchType.EAGER)
    public Resource getResource() {
        return resource;
    }
 
    public void setResource(Resource resource) {
        this.resource = resource;
    }
 
    @ManyToOne
    public ResourceProperty getProperty() {
        return property;
    }
 
    public void setProperty(ResourceProperty property) {
        this.property = property;
    }
     
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
  
        ResourcePropertyValueId that = (ResourcePropertyValueId) o;
  
        if (resource != null ? !resource.equals(that.resource) : that.resource != null) return false;
        if (property != null ? !property.equals(that.property) : that.property != null)
            return false;
  
        return true;
    }
  
    public int hashCode() {
        int result;
        result = (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (property != null ? property.hashCode() : 0);
        return result;
    }
}
