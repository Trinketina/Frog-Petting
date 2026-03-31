package net.trinketina.frogpetting;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;

public interface IPettingSound {
    default SoundEvent frog_Petting$getPettingSound(String entity_id) {
        if (PettingData.OFFSETS.containsKey(entity_id)) {
            String sound_event_id = PettingData.OFFSETS.get(entity_id).sound_event_id;
            if (sound_event_id != null) {
                return SoundEvent.createVariableRangeEvent(Identifier.parse(sound_event_id));
            }
        }
        return null;
    }
    default SoundEvent frog_Petting$getPettingAmbientSound() {
        return null;
    }
    default float frog_Petting$getPettingSoundVolume() {
        return 1.0f;
    }
    default float frog_Petting$getPettingSoundPitch(RandomSource random) {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
        //return  (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }
}
