package com.highwaycenter.role.model;



/**
 * HJcRyjs entity. @author MyEclipse Persistence Tools
 */

public class HJcRyjs  implements java.io.Serializable {


	private static final long serialVersionUID = 1011470950940290089L;
    // Fields    

	private HJcRyjsId id;


    // Constructors

    /** default constructor */
    public HJcRyjs() {
    }

    
    /** full constructor */
    public HJcRyjs(HJcRyjsId id) {
        this.id = id;
    }

   
    // Property accessors

    public HJcRyjsId getId() {
        return this.id;
    }
    
    public void setId(HJcRyjsId id) {
        this.id = id;
    }
   








}