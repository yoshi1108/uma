package uma.view

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.beans.javax_swing_border_MatteBorder_PersistenceDelegate;

import groovy.swing.SwingBuilder

import javax.swing.BoxLayout;
import javax.swing.JButton
import javax.swing.JList

import uma.AllMain;
import uma.CliborInput;
import uma.Excel2Data;
import uma.JraWeb;
import uma.Misyori;
import uma.Prop;
import uma.RaceInfo;

class MainWindow {
    static def swing = new SwingBuilder()
    static def manData
    static List raceInfoList
    static JList raceList
    
    javax.swing.JFrame fj;
    JButton hoge;
    
    static main(args){
        def frame = swing.frame(title:'Frame', size:[1200, 750], resizable:false, pack: true) {
            boxLayout(axis:BoxLayout.Y_AXIS)
            panel = swing.panel(preferredSize:[1200, 40]) {
                button(text:'騎手・調教データ読み込み', actionPerformed: {manData=Excel2Data.excel2Data();})
                button(text:'cliborデータ取得', actionPerformed: {raceInfo=CliborInput.parseFile();})
                button(text:'Web馬柱データ取得', actionPerformed: {
                    JraWeb.getUmaBashiraData();
                    raceInfoList = JraWeb.parseFileUB();
                    raceList.listData = (raceInfoList).collect {"${it.raceStatus}"}
                })
                button(text:'Web結果データ取得', actionPerformed: {
                    JraWeb.getResultData();
                    raceInfoList = JraWeb.parseFileRS();
                    raceList.listData = (raceInfoList).collect {"${it.raceStatus}"}
                })
                button(text:'設定', actionPerformed: {SettingWindow.main()})
            }
            scrollPane(preferredSize:[1000, 200]) {
                raceList = list( id:'listId', keyReleased: {misyori(raceList.getSelectedIndex())},
                mouseClicked: {misyori(raceList.getSelectedIndex())})
            }
            scrollPane(preferredSize:[1000, 300]) {
                textArea( id: "outputArea2", text: '', columns :100, rows: 17)
            }
        }

        // 初期化
        AllMain.init();
        Prop.prop.each{println it}

        // 画面表示
        frame.show()
    }

    static misyori(int index){
        RaceInfo raceInfo = raceInfoList.get(raceList.getSelectedIndex())
        List umaList = Misyori.misyori(manData, raceInfo);
        String tmp = ""
        umaList.each{tmp += Misyori.tyuStr(it) + "\n"}
        swing."outputArea2".setText(tmp);
    }
}