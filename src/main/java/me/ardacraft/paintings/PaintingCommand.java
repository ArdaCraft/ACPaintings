package me.ardacraft.paintings;

import me.ardacraft.paintings.item.PaintingItem;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dags <dags@dags.me>
 */
public class PaintingCommand implements ICommand
{
    @Override
    public String getCommandName()
    {
        return "paint";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/paint <id> <variant>";
    }

    @Override
    public List<String> getCommandAliases()
    {
        return Collections.singletonList("paint");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (sender instanceof EntityPlayerMP)
        {
            if (args.length == 2)
            {
                String type = args[0].toLowerCase();
                String var = args[1];

                Optional<PaintingItem> item = ACPainting.items.values().stream()
                        .filter(i -> i.getIdName().toLowerCase().startsWith(type))
                        .findFirst();

                int meta = toInt(var, 0);

                if (item.isPresent() && meta <= 70)
                {
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) sender;
                    ItemStack itemStack = new ItemStack(item.get(), 1, meta);
                    entityPlayerMP.inventory.addItemStackToInventory(itemStack);
                }
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        if (args.length == 1)
        {
            String lookup = args[0].toLowerCase();

            return ACPainting.items.values()
                    .stream()
                    .map(PaintingItem::getIdName)
                    .filter(s -> s.toLowerCase().startsWith(lookup))
                    .sorted()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }

    @Override
    public int compareTo(ICommand o)
    {
        return 0;
    }

    private static int toInt(String in, int fallback)
    {
        try
        {
            return Integer.parseInt(in);
        }
        catch (NumberFormatException e)
        {
            return fallback;
        }
    }
}
