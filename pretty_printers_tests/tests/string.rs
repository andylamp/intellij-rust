// === LLDB TESTS ==================================================================================

// lldb-command:run

// lldb-command:print s1
// lldbg-check:[...]$0 = "A∆й中" [...]
// lldb-command:print s2
// lldbg-check:[...]$1 = "A∆й中" [...]
// lldb-command:print empty_s1
// lldbg-check:[...]$2 = "" [...]
// lldb-command:print empty_s2
// lldbg-check:[...]$3 = "" [...]


fn main() {
    let s1 = "A∆й中";
    let s2 = String::from(s1);
    let empty_s1 = "";
    let empty_s2 = String::from(empty_s1);
    print!(""); // #break
}
