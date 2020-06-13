package app.payload;

import lombok.Data;

@Data
public class InputRanges {
  public int  core_amount = -1;
  public int  clock_rate;
  public int  video_memory;
  public int  volume_ram;
  public int  volume_hdd;
  public int  volume_ssd;
}
