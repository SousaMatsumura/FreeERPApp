package main.java.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public enum CheckInstalled {
   MVCPP_2013_X86(1), MVCPP_2017_X86(2), MVCPP_2017_X64(3);

   private int id;
   public static final LinkedHashSet<String> GET_SET = getSet();

   CheckInstalled(int id){
      this.id = id;
   }

   public String getValue(){
      if(id == 1) return "Microsoft Visual C++ 2013 Redistributable (x86)";
      else if(id == 2) return "Microsoft Visual C++ 2017 Redistributable (x86)";
      else if(id == 3) return "Microsoft Visual C++ 2017 Redistributable (x64)";
      else throw new NullPointerException(("Id "+id+" don't exists"));
   }

   public String getCommand(){
      return "cmd /c powershell.exe \"(((Get-ChildItem \"HKLM:\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\")" +
         " | Where-Object { $_.GetValue( 'DisplayName' ) -like '*"+getValue()+"*' } ).Length -gt 0)" +
         " -or (((Get-ChildItem \"HKLM:\\Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\")" +
             " | Where-Object { $_.GetValue( 'DisplayName' ) -like '*"+getValue()+"*' } ).Length -gt 0)\"";
   }

   private static LinkedHashSet getSet(){
      final List<String> temp = Arrays.asList(
            CheckInstalled.MVCPP_2013_X86.getCommand(),
            CheckInstalled.MVCPP_2017_X86.getCommand(),
            CheckInstalled.MVCPP_2017_X64.getCommand());
      return new LinkedHashSet<>(Collections.unmodifiableSet(new LinkedHashSet<>(temp)));
   }
}
