package com.example.feng.leagueoflegend;

import java.util.ArrayList;

/**
 * Created by Feng on 12/2/2015.
 */
public class Champion {

    private String name;
    private String title;
    private String description;
    private String tagOne;
    private String tagTwo;
    private String health;
    private String healthRegen;
    private String mana;
    private String manaRegen;
    private String moveSpeed;
    private String attackDamage;
    private String attackSpeed;
    private String attackRange;
    private String armor;
    private String MR;
    private String image;
    private String id;

    public Champion( String id, String name, String title, String description, String tagOne,String tagTwo,
                     String health,  String healthRegen,String mana, String manaRegen,String moveSpeed, String attackDamage,
                     String attackSpeed, String attackRange, String armor,String MR, String image)
    {
        this.name = name;
        this.title = title;
        this.description = description;
        this.tagOne = tagOne;
        this.id = id;
        this.image = image;
        this.MR = MR;
        this.armor = armor;
        this.attackRange = attackRange;
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.moveSpeed = moveSpeed;
        this.mana = mana;
        this.manaRegen = manaRegen;
        this.healthRegen = healthRegen;
        this.health = health;
        this.tagTwo = tagTwo;
    }

    public String getTagTwo() {
        return tagTwo;
    }

    public void setTagTwo(String tagTwo) {
        this.tagTwo = tagTwo;
    }

    public String getTagOne() {
        return tagOne;
    }

    public void setTagOne(String tagOne) {
        this.tagOne = tagOne;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManaRegen() {
        return manaRegen;
    }

    public void setManaRegen(String manaRegen) {
        this.manaRegen = manaRegen;
    }

    public String getMana() {
        return mana;
    }

    public void setMana(String mana) {
        this.mana = mana;
    }

    public String getHealthRegen() {
        return healthRegen;
    }

    public void setHealthRegen(String healthRegen) {
        this.healthRegen = healthRegen;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(String moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public String getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(String attackDamage) {
        this.attackDamage = attackDamage;
    }

    public String getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(String attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public String getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(String attackRange) {
        this.attackRange = attackRange;
    }

    public String getMR() {
        return MR;
    }

    public void setMR(String MR) {
        this.MR = MR;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }



}
