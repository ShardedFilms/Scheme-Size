package mindustry.ui.dialogs;

import arc.*;
import arc.util.*;
import arc.func.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.graphics.g2d.*;
import mindustry.*;
import mindustry.ui.*;
import mindustry.ui.fragments.*;
import mindustry.ai.types.*;
import mindustry.gen.*;
import mindustry.game.*;
import mindustry.content.*;

public class AISelectDialog extends BaseDialog{

    private AIController ai;
	private Table content = new Table();
	private PlayerSelectFragment list = new PlayerSelectFragment();

	public AISelectDialog(String name){
		super(name);
		addCloseButton();

        content.button(Icon.none, () -> ai = null).size(64).row();
		template(UnitTypes.mono, new MinerAI());

		list.build(cont);
		cont.add(table).padLeft(16f);

        Events.on(UnitChangeEvent.class, event -> {
            ai.unit(Vars.player.unit());
        });
	}

	private void template(Unit icon, AIController ai){
		var draw = icon.getIcon(Cicon.full);
		content.button(draw, () -> this.ai = ai).size(64).row();
	}

	public void select(boolean show, Cons<Player, AIController> callback){
		if(show){
            list.rebuild();
            show();
        }else callback.get(list.select(), ai);
	}
}