package business.carros.classes;

import business.campeonatos.Piloto;
import business.carros.Hibrido;

import java.util.Random;

public class C2H extends C2 implements Hibrido {
    private int motorEletrico;

    public C2H(int minCilindrada, int maxCilindrada, int fiabilidade, int potMotorEletrico){
        super(minCilindrada, maxCilindrada,fiabilidade);
        this.motorEletrico = potMotorEletrico;
    }

    @Override
    public int getPotenciaMotorEletrico() {
        return motorEletrico;
    }

    @Override
    public void setPotenciaMotorEletrico(int motorEletrico) {
        this.motorEletrico = motorEletrico;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }
        if (o.getClass() != C2H.class) {
            return false;
        }

        C2H c2H = (C2H) o;
        return this.getMinCilindrada() == c2H.getMinCilindrada() &&
               this.getMaxCilindrada() == c2H.getMaxCilindrada() && 
               this.getFiabilidade() == c2H.getFiabilidade() && 
               this.motorEletrico == c2H.motorEletrico;
    }

    @Override
    public int hashCode() {
        Integer me = motorEletrico;
        return super.hashCode() + me.hashCode();
    }

    public C2H clone(){
        return new C2H(super.getMinCilindrada(),super.getMaxCilindrada(),super.getFiabilidade(),this.motorEletrico);
    }

    @Override
    public String toString() {
        return "C2H";
    }

    @Override
    public boolean dnf(int cilindrada, int volta, Piloto piloto) {
        Random rand=new Random();
        int x=rand.nextInt(90);
        int motorH = this.getPotenciaMotorEletrico()/20;
        return (x > super.getFiabilidade() + (cilindrada/1200) - motorH);
        //return false;
    }

    @Override
    public boolean isHibrid() {
        return true;
    }
}
