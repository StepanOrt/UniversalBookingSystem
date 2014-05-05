package cz.cvut.fit.ortstepa.universalbookingsystem.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cz.cvut.fit.ortstepa.universalbookingsystem.domain.helper.Action;

@Entity
@Table(name = "rule")
public class Rule {
	
	private Long id;
	private String name, expression;
	private boolean enabled = true;
	private Action action;
	private PriceChange priceChange;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() { return id; }

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@NotNull
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "action")
	@Enumerated(value=EnumType.ORDINAL)
	public Action getAction() {
		return action;
	}

	public void setAction(Action event) {
		this.action = event;
	}
	
	@ManyToOne
	@JoinColumn(name = "price_change_id", nullable = false)
    public PriceChange getPriceChange() {
        return priceChange;
    }
     
    public void setPriceChange(PriceChange priceChange) {
        this.priceChange = priceChange;
    }

	
}
