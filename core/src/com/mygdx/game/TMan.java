package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class TMan {
  private AssetManager assMan;
  private HashMap<String, TextureRegion> regionCache;

  private   static final String SND_PATH    = "sound/";
  private   static final String ATLAS_PATH  = "textureAtlas/";

  TMan() {
    assMan = new AssetManager();
    regionCache = new HashMap<>();
  }

  public void load() {
    List<String> sounds = listFile(SND_PATH, ".mp3");
    for (String snd : sounds)
      assMan.load(SND_PATH + snd, Sound.class);

    List<String> atlases = listFile(ATLAS_PATH, ".atlas");
    for (String atlas : atlases)
      assMan.load(ATLAS_PATH + atlas, TextureAtlas.class);

    assMan.finishLoading();

    for (String atlas : atlases) {
      TextureAtlas ta = assMan.get(ATLAS_PATH + atlas, TextureAtlas.class);
      Array<TextureAtlas.AtlasRegion> regions =  ta.getRegions();
      for (TextureAtlas.AtlasRegion region : regions) {
        TextureRegion tg = ta.findRegion(region.name);
        tg.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        regionCache.put(region.name, tg);
      }
    }
  }


  private List<String> listFile(String path, String ext) {
    FileHandle dirHandle;
    dirHandle = Gdx.files.internal(path);
    FileHandle[] fhs = dirHandle.list();
    List<String> res = new ArrayList<>();
    for (FileHandle fh : fhs) {
      if (fh.name().contains(ext))
        res.add(fh.name());
    }
    return res;
  }


  /************************************************************************************************/
  public Sound getSound(String key) {
    return assMan.get(SND_PATH + key + ".mp3");
  }

  public TextureRegion getTG(String key) {
    return regionCache.get(key);
  }
}
