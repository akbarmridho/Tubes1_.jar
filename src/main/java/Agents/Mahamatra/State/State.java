package Agents.Mahamatra.State;

import Agents.Agent;

public interface State extends Agent {
    /* Fungsi yang menanyakan apakah ingin melepaskan kontrol terhadap kapal
     * termasuk ke pelepasan kontrol secara sukarela
     */
    public boolean isFinished();

    /* Saat agen ingin memilih strategi, setiap strategi berhak mengajukan prioritas strateginya
     */
    public int measureTakeoverPriority();

    /* Bila dirasa mendesak, sebuah strategi berhak mengajukan pengambilalihan strategi
     * dengan prioritas tertentu
     */
    public int measureInterruptionPriority();

    /* Fungsi yang dipanggil ketika strategi menerima kontrol
     */
    public void receiveControl();
}
