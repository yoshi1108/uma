package uma.view

import java.awt.BorderLayout;
import java.awt.Dimension;

import groovy.swing.SwingBuilder

import javax.swing.BoxLayout;
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JTextField;

import uma.AllMain;
import uma.CliborInput;
import uma.Excel2Data;
import uma.JraWeb;
import uma.Misyori;
import uma.Prop;
import uma.RaceInfo;

class SettingWindow {
    static SwingBuilder swing = new SwingBuilder()
    static JFrame frame
    static save() {
        
        for(String key:Prop.prop.keySet()){
            Prop.prop.put(key, swing."value-${key}".getText())
        }
        Prop.save();
        Prop.load();
        frame.dispose()
    }
    static main(args){
        AllMain.init();
        frame = swing.frame(title:'Frame', size:[400, 100], resizable:false, pack: true) {
            boxLayout(axis:BoxLayout.Y_AXIS)
            for(String key: Prop.prop.keySet()) {
                panel(preferredSize:[400, 30]) {
                    label(text:key, preferredSize:[120, 30])
                    textField(id: "value-${key}", text:Prop.prop.get(key), preferredSize:[120, 30])
                }
            }
            panel(preferredSize:[400, 50]) {
                button(text:'更新', actionPerformed: {save()})
                button(text:'キャンセル', actionPerformed: {frame.dispose()})
            }
            panel(preferredSize:[400, 5]){}
        }
        // 画面表示
        frame.show()
    }
}