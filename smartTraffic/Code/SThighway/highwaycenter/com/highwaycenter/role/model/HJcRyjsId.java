package com.highwaycenter.role.model;

import com.highwaycenter.gljg.model.HJcGlry;
/**
 * HJcRyjsId entity. @author MyEclipse Persistence Tools
 */

public class HJcRyjsId  implements java.io.Serializable {


    // Fields    

	private static final long serialVersionUID = 482997168253492530L;
	private HJcGlry HJcGlry;
     private HJcJbjsb HJcJbjsb;


    // Constructors

    /** default constructor */
    public HJcRyjsId() {
    }

    
    /** full constructor */
    public HJcRyjsId(HJcGlry HJcGlry, HJcJbjsb HJcJbjsb) {
        this.HJcGlry = HJcGlry;
        this.HJcJbjsb = HJcJbjsb;
    }

   
    // Property accessors

    public HJcGlry getHJcGlry() {
        return this.HJcGlry;
    }
    
    public void setHJcGlry(HJcGlry HJcGlry) {
        this.HJcGlry = HJcGlry;
    }

    public HJcJbjsb getHJcJbjsb() {
        return this.HJcJbjsb;
    }
    
    public void setHJcJbjsb(HJcJbjsb HJcJbjsb) {
        this.HJcJbjsb = HJcJbjsb;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof HJcRyjsId) ) return false;
		 HJcRyjsId castOther = ( HJcRyjsId ) other; 
         
		 return ( (this.getHJcGlry()==castOther.getHJcGlry()) || ( this.getHJcGlry()!=null && castOther.getHJcGlry()!=null && this.getHJcGlry().equals(castOther.getHJcGlry()) ) )
 && ( (this.getHJcJbjsb()==castOther.getHJcJbjsb()) || ( this.getHJcJbjsb()!=null && castOther.getHJcJbjsb()!=null && this.getHJcJbjsb().equals(castOther.getHJcJbjsb()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHJcGlry() == null ? 0 : this.getHJcGlry().hashCode() );
         result = 37 * result + ( getHJcJbjsb() == null ? 0 : this.getHJcJbjsb().hashCode() );
         return result;
   }   





}