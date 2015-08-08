package kz.ef.art.vision;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


class CheckBox extends JCheckBox implements ItemListener {

    private Boolean trigger;

    public CheckBox(String title, Boolean t) {
        this.trigger = t;
        setText(title);
        setSelected(trigger);
        addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        trigger = ((JCheckBox) e.getSource()).isSelected();
        System.out.println(trigger);
    }

}
